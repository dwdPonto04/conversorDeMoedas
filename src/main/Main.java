package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, Double> exchangeRates = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("\nSeja bem-vindo ao Conversor de Moedas\n");

        Scanner leitor = new Scanner(System.in);
        ConsumoApi consumoApi = new ConsumoApi();

        // Definindo a moeda base como USD por padrão
        String baseCurrency = "USD";
        consumoApi.getExchangeRates(baseCurrency); // Obtém as taxas de câmbio inicialmente

        int opcao = 0;
        double valor = 0;

        while (opcao != 7) {
            System.out.println("\nOperações\n");
            System.out.println("1- Dólar =>> Peso argentino");
            System.out.println("2- Peso argentino =>> Dólar");
            System.out.println("3- Dólar =>> Real brasileiro");
            System.out.println("4- Real brasileiro =>> Dólar");
            System.out.println("5- Dólar =>> Peso colombiano");
            System.out.println("6- Peso colombiano =>> Dólar");
            System.out.println("7- Sair");
            System.out.print("Escolha uma opção válida: ");

            opcao = leitor.nextInt();
            if (opcao >= 1 && opcao <= 6) {
                System.out.print("Digite o valor que deseja converter: ");
                valor = leitor.nextDouble();
            }

            switch (opcao) {
                case 1: // Dólar para Peso Argentino
                    System.out.printf("Valor em Peso Argentino: %.2f ARS\n", valor * getExchangeRate("ARS", baseCurrency));
                    break;

                case 2: // Peso Argentino para Dólar
                    System.out.printf("Valor em Dólar: %.2f USD\n", valor / getExchangeRate("ARS", baseCurrency));
                    break;

                case 3: // Dólar para Real Brasileiro
                    System.out.printf("Valor em Real Brasileiro: %.2f BRL\n", valor * getExchangeRate("BRL", baseCurrency));
                    break;

                case 4: // Real Brasileiro para Dólar
                    System.out.printf("Valor em Dólar: %.2f USD\n", valor / getExchangeRate("BRL", baseCurrency));
                    break;

                case 5: // Dólar para Peso Colombiano
                    System.out.printf("Valor em Peso Colombiano: %.2f COP\n", valor * getExchangeRate("COP", baseCurrency));
                    break;

                case 6: // Peso Colombiano para Dólar
                    System.out.printf("Valor em Dólar: %.2f USD\n", valor / getExchangeRate("COP", baseCurrency));
                    break;

                case 7:
                    System.out.println("Saindo... Obrigado por usar nosso sistema.");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

        leitor.close();
    }

    private static double getExchangeRate(String targetCurrency, String baseCurrency) {
        return exchangeRates.getOrDefault(targetCurrency, 1.0);
    }

    public static void storeExchangeRates(Map<String, Double> rates) {
        exchangeRates.putAll(rates);
    }
}
