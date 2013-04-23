/* $Id$ */
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

const unsigned int MAX_INT = 4294967295;

typedef struct Node
{
  struct Node *children[2];
  unsigned int *data;
}node;

typedef struct trieset
{
  node *root;
}trie;

trie *new_trieset()
{
  trie *new_set = malloc(sizeof(trie));  
  new_set->root = malloc(sizeof(node));
  new_set->root->children[0] = NULL;
  new_set->root->children[1] = NULL;
  new_set->root->data = malloc(sizeof(unsigned int*));
  new_set->root->data = NULL;
  return new_set;
}

int trieset_lookup(trie *this, unsigned int item)
{
  int bit;
  node *p;
  p = this->root;
  
  if(p == NULL)
  {
    return 0;//if trie is emtpy return
  }
  
  /**
   *traverses through tree
   *if NULL node is found return 0
   *otherwise if for reaches the end the item is present in the trie and return 1
   **/
  for(int i=1; i<(sizeof(unsigned int)*8)+1; i++)
  {
    bit = (item >> ((sizeof(unsigned int)*8)-i))&1;

    if(p->children[bit] == NULL)
    {
      return 0;
    }
    p = p->children[bit];
  }
  return 1;
}

int trieset_remove(trie *this, unsigned int item)
{
  if(trieset_lookup(this, item) == 0)
  {
    return 0;
  }
  /**
   *traverses through trie
   *until the last node is reached
   *then sets that node to null
   **/
  node *prev;
  node *p = this->root;
  int depth = 1;
  int bit;

  while(depth < (sizeof(unsigned int)*8)+1)
  {
    prev = p;
    bit = (item >> ((sizeof(unsigned int)*8)-depth))&1;
    depth++;
    p = p->children[bit];
  }
  prev->children[bit] = NULL;
  return 1;
}

int trieset_add(trie *this, unsigned int item)
{
  if(trieset_lookup(this, item) == 1)
  {
    trieset_remove(this, item);//if the element is in the trie already remove it
    return 0;
  }

  node *p = this->root;
  int depth = 1;
  int bit;
  /**
   *traverses through tree and if the path is null 
   *add to the path
   *until it reaches the depth
   *and makes new node and stores data
   **/
  while(depth < (sizeof(unsigned int)*8)+1)
  { 
    bit = (item >> ((sizeof(unsigned int)*8)-depth))&1;
    depth++;
   
    if(p->children[bit] == NULL)
    {
      p->children[bit] = malloc(sizeof(node));
      p->children[bit]->children[0] = NULL;
      p->children[bit]->children[1] = NULL;
      p->children[bit]->data = malloc(sizeof(unsigned int*));
      p->children[bit]->data = NULL;
    }
    p = p->children[bit];
   
  }
  
  p->data = malloc(sizeof(unsigned int*));
  *(p->data) = item;
 
  return 1;
}

int trieset_is_empty(trie *this)
{
  return this->root == NULL;
}

void trieset_write_out(node *root)
{
  if(root != NULL)
  {
    if(root->children[0] == NULL && root->children[1] == NULL && root->data != NULL)
    {
      printf("%u\n", *(root->data));//if element node print data
    }

    if(root->children[0] != NULL)
    {
      trieset_write_out(root->children[0]);//recursively go left
    }

    if(root->children[1] != NULL)
    {
      trieset_write_out(root->children[1]);//recursively go right
    }
  }else{
    printf("%s\n", "Trie is empty");//if trie is empty
  }
}
int main()
{
  trie *tree = new_trieset();
  int i = 0;
  int n = 0;
  srand(time(NULL));

  printf("%s\n", "Test before random number generator, please add 3 numbers to the trie");
  unsigned int user;
  while(n < 3){
  scanf("%u", &user);
  trieset_add(tree,user);
  n++;
  }
  printf("\ntrieset is:\n");
  trieset_write_out(tree->root);
  n = 0;
  printf("%s\n", "please remove 2 numbers from the trie");
  while(n < 2){
  scanf("%u", &user);
  trieset_remove(tree, user);
  n++;
  }
  
  printf("\ntrieset with random number:\n");
  while(i < 10)//add i amount of numbers
  {
    
    unsigned int r = rand()%(MAX_INT);//create random unsigned integer
    trieset_add(tree, r); //add integer to tree
    i++;
  }

  trieset_write_out(tree->root);  
  return 0;
}
