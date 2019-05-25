import java.util.LinkedList;
import java.util.Scanner;
public class Test {
 public static void main(String[] args) {
  Scanner scanner = new Scanner(System.in);
  String referencia = scanner.nextLine();
  String[] stringReferencia = referencia.split(";");

  AlgoritmoDeSubstituicao sc = new AlgoritmoSegundaChance(3);

  for (int i = 0; i < (stringReferencia.length - 1); i++) {
   sc.inserir(stringReferencia[i]);
   // sc.imprimirQuadros();

  }
  System.out.println("Page Faults: " + sc.getPageFaultCount());

 }
}

abstract class AlgoritmoDeSubstituicao {
 protected int numeroDeFalhas;
 protected int numeroDeQuadros;
 LinkedList quadros;

 public AlgoritmoDeSubstituicao(int numeroDeQuadros) {
  if (numeroDeQuadros < 0)
   throw new IllegalArgumentException();
  this.numeroDeQuadros = numeroDeQuadros;
  numeroDeFalhas = 0;
 }

 public int getPageFaultCount() {
  return numeroDeFalhas;
 }

 public abstract void inserir(String pageNumber);

 public void imprimirQuadros() {
  System.out.print("Quadros:  ");
  for (int i = 0; i < quadros.size(); i++) {
   System.out.print(quadros.get(i) + " ");
  }
  System.out.println();
 }
}

class AlgoritmoSegundaChance extends AlgoritmoDeSubstituicao {
 LinkedList bits;
 private static int PONTEIRO = 0;

 public AlgoritmoSegundaChance(int numeroDeQuadros) {
  super(numeroDeQuadros);
  this.quadros = new LinkedList();
  this.bits = new LinkedList();

 }

 @Override
 public void inserir(String pageNumber) {
  int tmp = quadros.indexOf(pageNumber);

  // caso a pagina ainda nao esteja na memoria
  if (tmp == -1) {
   if (quadros.size() < numeroDeQuadros) {
    quadros.add(pageNumber);
    bits.add(0);
   } else {
    while (bits.get(PONTEIRO) == 1) {
     bits.set(PONTEIRO, 0);
     PONTEIRO++;
     // ponteiro voltando ao inicio
     if (PONTEIRO == numeroDeQuadros) {
      PONTEIRO = 0;
     }
    }
    // substituicao
    quadros.remove(PONTEIRO);
    bits.remove(PONTEIRO);
    quadros.add(PONTEIRO, pageNumber);
    bits.add(PONTEIRO, 0);

    PONTEIRO++;
    // ponteiro voltando ao inicio
    if (PONTEIRO == numeroDeQuadros) {
     PONTEIRO = 0;
    }
   }
   numeroDeFalhas++;
  } else { // se a pagina ja esta na memoria
   bits.set(tmp, 1);

  }
 }

 @Override
 public void imprimirQuadros() {
  System.out.print("Quadros:  ");
  for (int i = 0; i < quadros.size(); i++) {
   System.out.print(quadros.get(i) + " bit: " + bits.get(i) + " ");
  }
  System.out.println();
 }
}

