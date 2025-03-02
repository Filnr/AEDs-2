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

bool verificaPalindromo(char entrada[], int fim,int inicio){
        // A variavel ja começa verdadeira, pois no primeiro falso acaba
		bool iguais = true;
		if(fim > inicio){
            // Verifica cada letra em busca de diferença, caso ocorra iguais vira falso e encerra a função
            if(entrada[inicio] != entrada[fim]){
                iguais = false;
            }
            else{
                verificaPalindromo(entrada, fim-1, inicio+1);
            }
        }
        return iguais;
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
		if(verificaPalindromo(str, tamanho, 0)){
			printf("SIM\n");
		}
		else{
			printf("NAO\n");
		}
        // Repetindo o processo de leitura da string
		fgets(str, sizeof(str), stdin);
		str[strcspn(str, "\n")] = '\0';
	}
}