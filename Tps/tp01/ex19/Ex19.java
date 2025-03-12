import java.util.Scanner;
public class Ex19 {
  /**
   * Substitui as letras A, B, C pelos valores das portas
   */
  private static String substituirValores(int[] portas, String expressao) {
    StringBuilder resultado = new StringBuilder();
    for (int i = 0; i < expressao.length(); i++) {
      char caracterAtual = expressao.charAt(i);
      if (caracterAtual != ' ') {
        if (caracterAtual == 'A')
          resultado.append(portas[0]);
        else if (caracterAtual == 'B')
          resultado.append(portas[1]);
        else if (caracterAtual == 'C')
          resultado.append(portas[2]);
        else
          resultado.append(caracterAtual);
      }
    }
    return resultado.toString();
  }

  /**
   * Processa operações de negação (NOT)
   */
  private static String processarNao(String expressao) {
    StringBuilder resultado = new StringBuilder();
    for (int i = 0; i < expressao.length(); i++) {
      if (expressao.charAt(i) == 'n' && i + 4 < expressao.length() &&
          expressao.charAt(i + 2) == 't' &&
          (expressao.charAt(i + 4) == '1' || expressao.charAt(i + 4) == '0')) {
        resultado.append(expressao.charAt(i + 4) == '1' ? 0 : 1);
        i += 5;
      } else
        resultado.append(expressao.charAt(i));
    }
    return resultado.toString();
  }

  /**
   * Processa operações lógicas AND
   */
  private static String processarE(String expressao) {
    StringBuilder resultado = new StringBuilder();
    for (int i = 0; i < expressao.length(); i++) {
      if (i + 1 < expressao.length() && expressao.charAt(i) == 'a' && expressao.charAt(i + 1) == 'n') {
        if (i + 7 < expressao.length() && expressao.charAt(i + 7) == ')') {
          char valor = (expressao.charAt(i + 4) == '1' && expressao.charAt(i + 6) == '1') ? '1' : '0';
          resultado.append(valor);
          i += 7;
        }
        else if (i + 9 < expressao.length() && expressao.charAt(i + 9) == ')') {
          char valor = (expressao.charAt(i + 4) == '1' && expressao.charAt(i + 6) == '1' &&
                      expressao.charAt(i + 8) == '1') ? '1' : '0';
          resultado.append(valor);
          i += 9;
        }
        else if (i + 11 < expressao.length() && expressao.charAt(i + 11) == ')') {
          char valor = (expressao.charAt(i + 4) == '1' && expressao.charAt(i + 6) == '1' &&
                      expressao.charAt(i + 8) == '1' && expressao.charAt(i + 10) == '1') ? '1' : '0';
          resultado.append(valor);
          i += 11;
        }
        else
          resultado.append(expressao.charAt(i));
      } else
        resultado.append(expressao.charAt(i));
    }
    return resultado.toString();
  }

  /**
   * Processa operações lógicas OR
   */
  private static String processarOu(String expressao) {
    StringBuilder resultado = new StringBuilder();
    for (int i = 0; i < expressao.length(); i++) {
      if (i + 1 < expressao.length() && expressao.charAt(i) == 'o' && expressao.charAt(i + 1) == 'r') {
        if (i + 6 < expressao.length() && expressao.charAt(i + 6) == ')') {
          char valor = (expressao.charAt(i + 3) == '1' || expressao.charAt(i + 5) == '1') ? '1' : '0';
          resultado.append(valor);
          i += 6;
        }
        else if (i + 8 < expressao.length() && expressao.charAt(i + 8) == ')') {
          char valor = (expressao.charAt(i + 3) == '1' || expressao.charAt(i + 5) == '1' ||
                      expressao.charAt(i + 7) == '1') ? '1' : '0';
          resultado.append(valor);
          i += 8;
        }
        else if (i + 10 < expressao.length() && expressao.charAt(i + 10) == ')') {
          char valor = (expressao.charAt(i + 3) == '1' || expressao.charAt(i + 5) == '1' ||
                      expressao.charAt(i + 7) == '1' || expressao.charAt(i + 9) == '1') ? '1' : '0';
          resultado.append(valor);
          i += 10;
        }
        else
          resultado.append(expressao.charAt(i));
      } else
        resultado.append(expressao.charAt(i));
    }
    return resultado.toString();
  }

  /**
   * Processa expressão de forma recursiva
   */
  private static String processarExpressao(String expressao, int indiceInicio) {
    int tamanho = expressao.length() - 1;
    if (indiceInicio == tamanho)
      return expressao;
    else if (expressao.charAt(indiceInicio) == 'n')
      return processarNao(processarExpressao(expressao, indiceInicio + 4));
    else if (expressao.charAt(indiceInicio) == 'o')
      return processarOu(processarExpressao(expressao, indiceInicio + 3));
    else if (expressao.charAt(indiceInicio) == 'a')
      return processarE(processarExpressao(expressao, indiceInicio + 4));
    else
      return processarExpressao(expressao, ++indiceInicio);
  }

  /**
   * Inicia o processamento da expressão
   */
  private static String avaliarExpressao(String expressao) {
    return processarExpressao(expressao, 0);
  }

  /**
   * Método principal que controla a entrada/saída
   */
  public static void main(String[] args) {
    Scanner entrada = new Scanner(System.in);
    int numPortas = entrada.nextInt();
    String expressao;
    int[] portas = new int[3];

    while (numPortas != 0) {
      for (int i = 0; i < numPortas; i++)
        portas[i] = entrada.nextInt();
      expressao = entrada.nextLine();
      expressao = substituirValores(portas, expressao);
      expressao = avaliarExpressao(expressao);
      System.out.println(expressao);
      numPortas = entrada.nextInt();
    }
    entrada.close();
  }
}
