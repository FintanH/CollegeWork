#include <iostream>
#include <fstream>
#define ptr_bits 11
#define length_bits 4
#define threshold 1
#define buffer_size (1 << ptr_bits)
#define lookahead_size ((1 << length_bits) + threshold)

using namespace std;

class LZSS
{
private:
    unsigned int bit_buffer;
    int bit_mask;
    unsigned long codecount;
    unsigned long textcount;
    unsigned char *buffer;
public:
    LZSS();
    FILE *inFile;
    FILE *outFile;
    FILE *encodedFile;
    FILE *decodedFile;
    void set_bit_1();
    void set_bit_0();
    void flush_bit_buffer();
    void add_char_to_buffer(int c);
    void add_pointer_to_buffer(int offset, int length);
    void encode(char *inFile_name, char *outFile_name);
    int getbit(int bits);
    void decode(char *encodedFile_name, char *decodedFile_name);
};

LZSS::LZSS()
{
    buffer = new unsigned char[buffer_size*2];
    bit_buffer = 0; //hold binary representation of output
    bit_mask = 128; //8 bit mask
    codecount = 0; //size of compressed file
    textcount = 0; //size of file to be compressed
}

void LZSS::set_bit_0()
{
    /*
    *Dont set bit_buffer with mask
    *Shift mask to represent the 0 bit in the bit buffer and go to next bit position
    *Output bit_buffer as a char to the outFile and reset values once 8 bits are computed;
    */
    if((bit_mask >>= 1)==0)
    {
        fputc(bit_buffer,outFile);
        bit_buffer = 0;
        bit_mask = 128;
        codecount++;
    }
}

void LZSS::set_bit_1()
{
    /*
    *Set bit_buffer with mask
    *Shift mask to represent the 1 bit in the bit buffer and go to next bit position
    *Output bit_buffer as a char to the outFile and reset values once 8 bits are computed;
    */
    bit_buffer |= bit_mask;
    if((bit_mask >>= 1)==0)
    {
        fputc(bit_buffer,outFile);
        bit_buffer = 0;
        bit_mask = 128;
        codecount++;
    }
}

void LZSS::flush_bit_buffer()
{
    /*
    *If the mask is not reset then flush the bit_buffer to file
    */
    if(bit_mask != 128)
    {
        fputc(bit_buffer, outFile);
        codecount++;
    }
}

void LZSS::add_char_to_buffer(int c)
{
    int mask;

    set_bit_0(); //tag bit
    /*
    *Mask c for each of its 8 bits and set the bit_buffer with 1's or 0's
    */
    mask = 256;
    while(mask >>= 1)
    {
        if(c & mask)
        {
            set_bit_1();
        }else{
            set_bit_0();
        }
    }
}

void LZSS::add_pointer_to_buffer(int offset, int length)
{
    int mask;

    set_bit_1(); //tag bit

    /*
    *Mask the offset size adding the offset pointer to the bit_buffer
    */
    mask = buffer_size;
    while(mask >>= 1)
    {
        if(offset & mask)
        {
            set_bit_1();
        }else{
            set_bit_0();
        }
    }

    /*
    *Mask the length size adding the length pointer to the bit_buffer
    */
    mask = 1<<length_bits;
    while(mask >>= 1)
    {
        if(length & mask)
        {
            set_bit_1();
        }else{
            set_bit_0();
        }
    }
}

void LZSS::encode(char *inFile_name, char *outFile_name)
{
    inFile = fopen(inFile_name, "rb");
    if(!inFile)
    {
        cout << "Error in opening file to encode" << endl;
        return;
    }

    outFile = fopen(outFile_name, "wb");
    if(!outFile)
    {
        cout << "Error in opening/creating file to output to" << endl;
    }

    int i;
    int j;
    int min_lookahead;
    int offset;
    int match_length;
    int lookahead_index;
    int searchbuffer_index;
    int bufferend;
    int c;

    /*
    *Fill search buffer
    */
    for(i = 0; i < buffer_size-lookahead_size; i++)
    {
        buffer[i] = ' ';
    }

    /*
    *Fill look ahead buffer
    */
    for(i = buffer_size-lookahead_size; i < buffer_size*2; i++)
    {
        if ((c = fgetc(inFile)) == EOF)
        {
            break;
        }
        buffer[i] = c;
        textcount++;
    }
    bufferend = i;
    lookahead_index = buffer_size - lookahead_size;
    searchbuffer_index = 0;

    /*
    *Look for matches to point to
    */
    while(lookahead_index < bufferend)
    {
        min_lookahead = (lookahead_size <= bufferend - lookahead_index) ? lookahead_size : bufferend - lookahead_index;
        offset = 0;
        match_length = 1;
        c = buffer[lookahead_index];

        for (i = (lookahead_index - 1); i >= searchbuffer_index; i--)
        {
            if (buffer[i] == c) //check for start of match
            {
                for (j = 1; j < min_lookahead; j++) //if match continue looking further
                {
                   if (buffer[i + j] != buffer[lookahead_index + j])
                   {
                        break;
                   }
                }
                if (j > match_length) //if there is a match larger than 1 then set offset and length
                {
                    offset = i;
                    match_length = j;
                }
            }
        }

        /*
        *check match against threshold size
        *add character or add pointer
        */
        if(match_length <= threshold)
        {
            add_char_to_buffer(c);
        }else{
            add_pointer_to_buffer(offset & (buffer_size - 1), match_length - (threshold+1));
        }

        /*
        *Slide window by match_length
        */
        lookahead_index += match_length;
        searchbuffer_index += match_length;

        /*
        *if the look ahead has exceeded the size of the buffer array
        *then wrap the window and add the the new characters from the text
        */
        if(lookahead_index >= buffer_size * 2 - lookahead_size)
        {
            for (i = 0; i < buffer_size; i++)
            {
                buffer[i] = buffer[i + buffer_size];
            }

            bufferend -= buffer_size;
            lookahead_index -= buffer_size;
            searchbuffer_index -= buffer_size;

            while (bufferend < buffer_size * 2)
            {
                if ((c = fgetc(inFile)) == EOF){
                    break;
                }
                buffer[bufferend++] = c;
                textcount++;
            }
        }
    }
    flush_bit_buffer();
    fclose(inFile);
    fclose(outFile);
    cout << "text: " << textcount << "bytes" << endl;
    cout << "code: " << codecount << "bytes" << endl;
    cout << ((codecount * 100) / textcount) << "%" << endl;
}

int LZSS::getbit(int bits)
{
    int result;
    static int c, mask = 0;
    result = 0;

    /*
    *For the length of bits
    *Add the bits from c to result
    */
    for(int i = 0; i<bits; i++)
    {
        if(mask == 0)
        {
            if((c = fgetc(encodedFile)) == EOF)
            {
                return EOF;
            }
            mask = 128;
        }
        result <<= 1;
        if(c & mask)
        {
            result++;
        }
        mask >>= 1;
    }
    return result;
}


void LZSS::decode(char *encodedFile_name, char *decodedFile_name)
{
    encodedFile = fopen(encodedFile_name, "rb");
    if(!encodedFile)
    {
        cout << "Error in opening encoded file" << endl;
        return;
    }

    decodedFile = fopen(decodedFile_name, "wb");
    if(!decodedFile)
    {
        cout << "Error in opening/creating file to decode to" << endl;
    }
    int i, j, k;
    int lookahead_index;
    int c;

    /*
    *Fill search buffer
    */
    for(i = 0; i < buffer_size-lookahead_size; i++)
    {
        buffer[i] = ' ';
    }

    /*
    *Move bit by bit through file
    *If tag is a 0 decode single character
    *If tag is a 1 decode pointer
    */
    lookahead_index = buffer_size-lookahead_size;
    while((c = getbit(1)) != EOF)
    {
        if(!c)
        {
            if ((c = getbit(8)) == EOF)
            {
                break;
            }
            fputc(c,decodedFile);
            buffer[lookahead_index++] = c;
            lookahead_index &= (buffer_size - 1);
        }else{
            if ((i = getbit(ptr_bits)) == EOF)
            {
                break;
            }

            if ((j = getbit(length_bits)) == EOF)
            {
                break;
            }

            /*
            *Output pointer
            *Wrap search buffer
            */
            for(k = 0; k <= j+1; k++)
            {
                c = buffer[(i + k) & (buffer_size - 1)];
                fputc(c,decodedFile);
                buffer[lookahead_index++] = c;
                lookahead_index &= (buffer_size - 1);
            }
        }
    }
    fclose(encodedFile);
    fclose(decodedFile);
}

int main()
{
    LZSS *zip = new LZSS();
    zip->encode("text.txt","result.txt");
    zip->decode("result.txt","decoded.txt");
    return 0;
}
