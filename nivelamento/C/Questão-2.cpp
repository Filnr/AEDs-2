#include <iostream>

void buscaMaxMin(int array[], int tam, int *max, int *min)
{
    int i;
    if(tam % 2 == 0)
    {
        if(array[0] > array[1])
        {
            *max = array[0];
            *min = array[1];
        }
        else
        {
            *max = array[1];
            *min = array[0];
        }
        i = 2;
    }
    else 
    {
        *max = array[0];
        *min = array[0];
        i = 1;
    }
    while(i < tam - 1)
    {
        int maiorlocal, menorlocal;
        if(array[i] > array[i + 1])
        {
            maiorlocal = array[i];
            menorlocal = array[i + 1];
        }
        else
        {
            maiorlocal = array[i + 1];
            menorlocal = array[i];
        }
        if(maiorlocal > *max)
        {
            *max = maiorlocal;
        }
        if(menorlocal < *min)
        {
            *min = menorlocal;
        }
        i += 2;
    }
}

int main(void)
{
    int tam = 10;
    int array[tam] = {10, 2, 50, 4, 3, 100, 1, 5, 7, 9};
    int maior, menor;
    buscaMaxMin(array, tam, &maior, &menor);
    std::cout << "Maior numero: " << maior << std::endl;
    std::cout << "Menor numero: " << menor << std::endl;
}