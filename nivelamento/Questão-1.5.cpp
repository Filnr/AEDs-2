#include <iostream>

bool temElemento(int vetor[], int procurado, int tam)
{
    int i = (tam - 1) / 2;
    bool encontrado = false;
    while(i < tam && !encontrado && i >= 0)
    {
        if(vetor[i] == procurado)
        {
            encontrado = true;
        }
        else if(vetor[i] < procurado)
        {
            i++;
        }
        else
        {
            i--;
        }
    }
    return encontrado;
}

int main(void)
{
    int procurado, tam = 10;
    int vetor[tam] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    std::cout << "Digite o numero a ser procurado: ";
    std::cin >> procurado;
    bool encontrado = temElemento(vetor, procurado, tam);
    if(encontrado)
    {
        std::cout << "Elemento presente no array" << std::endl;
    }
    else 
        std::cout << "Elemento não presente no array" << std::endl;
    return 0;
}