//copyfile

#include <stdio.h>
#include <string.h>
void copyfile(char inname[10],char outname[10]);
int main(int a,char * args[])
{
    copyfile("a.txt","b.txt");
}
void copyfile(char inname[10],char outname[10])
{
    FILE * in,* out;
    int string;
    char name[50];
    strcpy(name,outname);

    in=fopen(inname,"r");
    out=fopen(name,"w");

    while((string=getc(in))!=EOF){
          putchar(string);
          putc(string,out);
    }
    in=NULL,out=NULL;
}
