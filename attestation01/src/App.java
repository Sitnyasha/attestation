import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Person> people = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        System.out.println("Введите покупателей в формате: Имя=Сумма (через запятую)");
        String[] peopleInput = scanner.nextLine().split("[,;]");
        for (String personData : peopleInput) {
            try {
                String[] parts = personData.trim().split("=");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Неверный формат: используйте Имя=Сумма");
                }
                String name = parts[0].trim();
                int money = Integer.parseInt(parts[1].trim());
                people.add(new Person(name, money));
            } catch (Exception e) {
                System.out.println("Ошибка ввода: " + e.getMessage());
                return;
            }
        }

        System.out.println("Введите продукты в формате: Название=Цена (через пробел или запятую)");
        String[] productsInput = scanner.nextLine().split("[,;]");
        for (String productData : productsInput) {
            try {
                String[] parts = productData.trim().split("=");
                String name = parts[0].trim();
                int price = Integer.parseInt(parts[1].trim());
                products.add(new Product(name, price));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        while (true) {
            System.out.println("Введите покупки в формате: Имя-Продукт (или END для завершения)");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("END")) {
                break;
            }

            try {
                String[] parts = input.split("-");
                String personName = parts[0].trim();
                String productName = parts[1].trim();

                Person person = people.stream()
                        .filter(p -> p.getName().equals(personName))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Покупатель не найден"));

                Product product = products.stream()
                        .filter(p -> p.getName().equals(productName))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Продукт не найден"));

                person.addProduct(product);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        System.out.println("\nРезультаты:");
        for (Person person : people) {
            System.out.println(person);
        }
    }
}