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

int fazSoma(char str[], int posicao){
    // Posicao começa com o tamanho da string
    int soma = 0;
    if(posicao >= 0){
        // Soma recebe o valor do numero atual + o resultado das recursões
        soma += (str[posicao] - '0') + fazSoma(str, posicao-1);
    }
    return soma;
}

int chamaSoma(char str[], int tamanho){
    // Função responsavel por chamar a função recursiva
    return fazSoma(str, tamanho);
}

int main(void){
    // Trocando a linguagem para ler as entradas
	setlocale(LC_ALL, "pt_BR.UTF-8");
    // Declarando a variavel que ira armazenar a string
	char str[10000];
	fgets(str, sizeof(str), stdin);
    // Removendo o \n do final da string para não dar erro na comparação
	str[strcspn(str, "\n")] = '\0';
	int tamanho;
	while(!ehFim(str))
	{
        // Tamanho recebe strlen - 1 para ser usado como indice
		tamanho = strlen(str) - 1;
        int soma = chamaSoma(str, tamanho);
        printf("%d\n", soma);
        // Repetindo o processo de leitura da string
		fgets(str, sizeof(str), stdin);
		str[strcspn(str, "\n")] = '\0';
	}
}