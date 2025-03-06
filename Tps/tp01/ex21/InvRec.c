#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <locale.h>

bool ehFim(char str[]){
    //Função que verifica se a entrada é o FIM
    //Inicialmente apenas compara se a entrada tem o tamanho certo para ser FIM depois verifica string
    bool ehFim = false;
    if(strlen(str) == 3){
        if(str[0] == 'F' && str[1] == 'I' && str[2] == 'M'){
            ehFim = true;
        }
    }
    return ehFim;
}

void inverteString(char entrada[], int i, int tamanho){
   if(i < tamanho){
        // Faz a substituição padrão de chars
        char temp;
        temp = entrada[i];
        entrada[i] = entrada[tamanho];
        entrada[tamanho] = temp;
        // chama a proxima recursão aumenta i e diminuindo tamanho
        inverteString(entrada, i+1, tamanho-1);
   }
}

void chamaRec(char entrada[]){
    int tamanho;
    tamanho = strlen(entrada) - 1;
    inverteString(entrada, 0, tamanho);
}

int main(void){
        // Trocando a linguagem para ler as entradas
	setlocale(LC_ALL, "pt_BR.UTF-8");
    // Declarando a variavel que ira armazenar a string
	char str[10000];
	fgets(str, sizeof(str), stdin);
    // Removendo o \n do final da string para não dar erro na comparação
	str[strcspn(str, "\n")] = '\0';
	while(!ehFim(str))
	{
        // Tamanho recebe strlen - 1 para ser usado como indice
        chamaRec(str);
        printf("%s\n", str);
        fgets(str, sizeof(str), stdin);
        str[strcspn(str, "\n")] = '\0';
    }
    return 0;
}