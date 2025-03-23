#include <stdio.h>
#include <string.h>
#include <stdbool.h>

void combinador(char str1[], char str2[], char resultado[]){
    int i, j, k;
    i = j = k = 0;
    while(str1[i] != '\0' && str2[j] != '\0'){
        resultado[k++] = str1[i++];
        resultado[k++] = str2[j++]; 
    }
    while(str1[i] != '\0'){
        resultado[k++] = str1[i++];
    }
    while(str2[j] != '\0'){
        resultado[k++] = str2[j++];
    }
    resultado[k] = '\0';
}

bool ehFim(char str[]){
    return (strcmp(str, "FIM") == 0);
}

int main(void){
    char palavra1[50];
    char palavra2[50];
    printf("Digite uma palavra: ");
    scanf("%s", palavra1);
    while(!ehFim(palavra1)){
        printf("Digite outra palavra: ");
        scanf("%s", palavra2);
        char combinada[100];
        combinador(palavra1, palavra2, combinada);
        printf("Resultado: %s\n\n", combinada);
        printf("Digite uma palavra: ");
        scanf("%s", palavra1);
    }
}