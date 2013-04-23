#include <iostream>
#include <fstream>
#include <queue>
#define MAX_CHAR 128

using namespace std;

class HuffmanTree
{
    class Node
    {
    public:
        Node(){};
        ~Node(){};
        int getFreq() const{return freq;}
        virtual void printHuffman(string encode)=0;
        virtual void printContents()
        {
            cout << "Frequency: " << freq << endl;
        }
    protected:
        int freq;
    };

    class CharNode:public Node
    {
    private:
        char c;
    public:
        CharNode(char ch, int frequency)
        {
            c = ch;
            freq = frequency;
        }
        void printHuffman(string encode)
        {
           encode += " ";
           if(c == ' ')
           {
               encode += "SPACE";
           }else if(c == '\n'){
               encode += "New Line";
           }else if(c == '\0'){
               encode += "NULL";
           }else{
               encode += c;
           }
           cout << encode << endl;
        }

        void printContents()
        {
            cout << "Char: " << c << endl;
            cout << "Frequency: " << freq << endl;
        }
    };

    class CompoundNode:public Node
    {
    private:
        string encode = "";
        Node *left;
        Node *right;
    public:
        CompoundNode(Node *l, Node *r)
        {
            left = l;
            right = r;
            freq = (l->getFreq() + r->getFreq());
        }

        void printHuffman(string e)
        {
            if(left)
            {
                encode = e;
                e += '0';
                left->printHuffman(e);
            }

            e = encode;
            if(right)
            {
                encode = e;
                e += '1';
                right->printHuffman(e);
            }
        }

        void printContents()
        {
            left->printContents();
            right->printContents();
        }
    };

    struct NodeCompare
    {
        bool operator()(const Node *left, const Node *right) const
        {
            return (left->getFreq() > right->getFreq());
        }
    };

private:
    std::priority_queue<Node*, std::vector<Node*>, NodeCompare> tree;

public:
    HuffmanTree(){};
    ~HuffmanTree(){};
    void build_tree(int *freqs);
};

void HuffmanTree::build_tree(int *freqs)
{
    Node *p;
    Node *q;
    Node *r;
    for(int i=0; i<MAX_CHAR; i++)
    {
        if(freqs[i] != 0)
        {
            p = new CharNode(i, freqs[i]);
            tree.push(p);
        }else{
            p = new CharNode(i, 1);
            tree.push(p);
        }
    }

    int counter = MAX_CHAR;

    while(counter > 1)
    {
        p = tree.top();
        tree.pop();
        q = tree.top();
        tree.pop();
        r = new CompoundNode(p, q);
        tree.push(r);
        counter--;
    }
    tree.top()->printHuffman("");
}

int main()
{
    char c;
    ifstream file;
    int *freqs = new int[MAX_CHAR];
    for(int i = 0; i<MAX_CHAR; i++)
    {
       freqs[i] = 0;
    }

    file.open("test.txt");
    if(!file)
    {
        cout << "Error in opening file" << endl;
        return 0;
    }

    while(!file.eof()) {
        c = file.get();
        freqs[c]++;
    }
    file.close();

    HuffmanTree *tree = new HuffmanTree();
    tree->build_tree(freqs);
    return 0;

}
