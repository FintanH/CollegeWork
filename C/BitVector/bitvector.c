/* $Id$ */
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <math.h>

#define MAX_LENGTH 255

typedef struct bitvector{
    unsigned int *set; //array set
    unsigned int set_size; //size of set
    unsigned int bit_size; //size parameter passed in bitset *bitset_new(int size)
}bitset;

bitset *bitset_new(int size)
{   
   bitset *new_set; //create new set
   new_set = (bitset *)malloc(sizeof(bitset)); //initialise new set
   new_set->bit_size = size; //initialise bit_size
   unsigned int qoutient = size/(sizeof(unsigned int)*8); //calculates set size
   if(size%(sizeof(unsigned int)*8) != 0){
	   qoutient++; //if overflow
   }
   new_set->set_size = qoutient; 
   new_set->set = (unsigned int *)malloc(sizeof(unsigned int)*qoutient); //initialises array
   for(int i=0; i<new_set->set_size; i++)
   {
	new_set->set[i] = 0; //free memory
   }
   return new_set;
}

int bitset_lookup(bitset *this, int item)
{ 
    int block = item/(sizeof(unsigned int)*8); //finds array position i.e. array[block]
    int position = item%(sizeof(unsigned int)*8); //finds bit position
    if(block > this->set_size || block < 0)
    {
	return -1; //out of bounds exception
    }
    
    unsigned int presence = this->set[block] & (1<<((sizeof(unsigned int)*8)-position-1)); //finds the bit in the list
    return (presence >> (sizeof(unsigned int)*8)-position-1); //places the bit at the start to return a 1 or 0
}

int bitset_add(bitset *this, int item)
{
    int block = item/(sizeof(unsigned int)*8);
    int position = item%(sizeof(unsigned int)*8);
    if(block > this->set_size || block < 0)
    {
	return 0;
    }
    if(bitset_lookup(this, item)==1)
    {
      return 1; //if bit is present already return
    }
    this->set[block] = (this->set[block] | (1 <<((sizeof(unsigned int)*8)-position-1))); //add the bit to the array
    return 1;
}

int bitset_remove(bitset *this, int item)
{
    int block = item/(sizeof(unsigned int)*8);
    int position = item%(sizeof(unsigned int)*8);
    if(block > this->set_size || block < 0)
    {
	return 0;
    }   
    this->set[block] = (this->set[block] & ~(1 << (sizeof(unsigned int)*8)-position-1)); //removes the bit from the array
    return 1;
}

void bitset_union(bitset *dest, bitset *src1, bitset *src2)
{
    for(int i=0; i<dest->bit_size; i++)
    {
	if(bitset_lookup(src1, i) == 1 || bitset_lookup(src2, i) == 1)
	{
	  bitset_add(dest, i); //if either set has that bit then add 
	}else{
	  bitset_remove(dest, i); //otherwise clear the bit in case re-use
	}
    } 
}

void bitset_intersect(bitset *dest, bitset *src1, bitset *src2)
{
    for(int i=0; i<dest->bit_size; i++)
    {
	if(bitset_lookup(src1, i) == 1 && bitset_lookup(src2, i) == 1)
	{
	  bitset_add(dest, i); //if both sets have that bit then add
	}else{
	  bitset_remove(dest, i); //otherwise clear the bit in case of re-use
	}
    }
}
int main()
{   
    char line1[MAX_LENGTH];
    char line2[MAX_LENGTH];

    printf("%s\n", "Enter first string");
    fgets(line1, MAX_LENGTH, stdin);

    printf("%s\n", "Enter second string");
    fgets(line2, MAX_LENGTH, stdin);

    bitset *set1 = bitset_new(128);
    bitset *set2 = bitset_new(128);
    bitset *dest = bitset_new(128);

    for(int i=0; i<strlen(line1); i++)
    {
	bitset_add(set1, (unsigned int)line1[i]); //add the strings contents to set
    }

    for(int i=0; i<strlen(line2); i++)
    {
	bitset_add(set2, (unsigned int)line2[i]);
    }
    
    printf("%s", "First Bit Vector set is:");
    for(int i=0; i<set1->bit_size; i++)
    {
	if(bitset_lookup(set1, i)==1)
	{
	  printf("%c", i); //prints the contents of set
	}
    }
    printf("\n");
    
    printf("%s", "Second Bit Vector set is:");
    for(int i=0; i<set2->bit_size; i++)
    {
	if(bitset_lookup(set2, i)==1)
	{
	  printf("%c", i);
	}
    }
    printf("\n");

    bitset_union(dest, set1, set2);
    printf("%s", "Union Bit Vector set is:");
    for(int i=0; i<dest->bit_size; i++)
    {
	if(bitset_lookup(dest, i)==1)
	{
	  printf("%c", i);
	}
    }
    printf("\n");
    
    bitset_intersect(dest, set1, set2);
    printf("%s", "Intersection Bit Vector set is:");
    for(int i=0; i<dest->bit_size; i++)
    {
	if(bitset_lookup(dest, i)==1)
	{
	  printf("%c", i);
	}
    }
    printf("\n");

    return 0;
}
