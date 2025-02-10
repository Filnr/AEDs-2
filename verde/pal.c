#include <stdio.h>
#include <string.h>
#include <stdbool.h>

int main(void){
	char str[1000];
	fgets(str, sizeof(str), stdin);
	str[strcspn(str, "\n")] = '\0';
	int tamanho;
	while(strcmp(str, "FIM") != 0)
	{
		tamanho = strlen(str) - 1;
		int i = 0;
		bool iguais = true;
		while(tamanho > i && iguais){
			if(str[i] < 65 || str[i] > 122){
				str[i] = '*';
			}
			if(str[tamanho] < 65 || str[tamanho] > 122){
				str[tamanho] = '*';
			}
			if(str[i] != str[tamanho]){
				iguais = false;
			}
			i++;
			tamanho--;
		}
		if(iguais){
			printf("SIM\n");
		}
		else{
			printf("NAO\n");
		}
		fgets(str, sizeof(str), stdin);
		str[strcspn(str, "\n")] = '\0';
	}
}
