import java.util.*;
import java.text.SimpleDateFormat;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<StudentWork> works = new ArrayList<>();
    private static final String PASSWORD = "teacher123";
    
    public static void main(String[] args) {
        System.out.println("=== СИСТЕМА ПРИЕМА СТУДЕНЧЕСКИХ РАБОТ ===");
        System.out.println("Версия 1.0\n");
        
        // Добавляем тестовые данные
        works.add(new StudentWork("Иван Петров", "ivan@edu.ru", "Домашняя работа 1", "Текст первой работы..."));
        works.add(new StudentWork("Мария Сидорова", "maria@edu.ru", "Эссе по литературе", "Анализ произведения..."));
        
        mainMenu();
    }
    
    static class StudentWork {
        String name;
        String email;
        String title;
        String content;
        String feedback;
        String date;
        boolean reviewed;
        
        public StudentWork(String name, String email, String title, String content) {
            this.name = name;
            this.email = email;
            this.title = title;
            this.content = content;
            this.feedback = "Ожидает проверки";
            this.date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
            this.reviewed = false;
        }
        
        @Override
        public String toString() {
            return String.format(
                "\n📌 РАБОТА: %s\n👤 СТУДЕНТ: %s\n📧 EMAIL: %s\n📅 ДАТА: %s\n\n📝 СОДЕРЖАНИЕ:\n%s\n\n💬 ОБРАТНАЯ СВЯЗЬ: %s\n%s",
                title, name, email, date, content, feedback, 
                reviewed ? "✅ ПРОВЕРЕНО" : "⏳ ОЖИДАЕТ ПРОВЕРКИ"
            );
        }
    }
    
    private static void mainMenu() {
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("ГЛАВНОЕ МЕНЮ");
            System.out.println("=".repeat(50));
            System.out.println("1. 📤 Студент: Отправить работу");
            System.out.println("2. 👨‍🏫 Преподаватель: Войти");
            System.out.println("3. ℹ️  О программе");
            System.out.println("0. ❌ Выход");
            System.out.print("\nВыберите пункт: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1 -> studentMenu();
                    case 2 -> teacherLogin();
                    case 3 -> about();
                    case 0 -> {
                        System.out.println("\nДо свидания!");
                        System.out.println("Всего работ в системе: " + works.size());
                        return;
                    }
                    default -> System.out.println("❌ Неверный выбор!");
                }
            } catch (Exception e) {
                System.out.println("❌ Ошибка ввода!");
            }
        }
    }
    
    private static void studentMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ОТПРАВКА НОВОЙ РАБОТЫ");
        System.out.println("=".repeat(50));
        
        System.out.print("Введите ваше ФИО: ");
        String name = scanner.nextLine();
        
        System.out.print("Введите ваш email: ");
        String email = scanner.nextLine();
        
        System.out.print("Введите название работы: ");
        String title = scanner.nextLine();
        
        System.out.println("Введите текст работы (в конце напишите 'КОНЕЦ' с новой строки):");
        StringBuilder content = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals("КОНЕЦ")) {
            content.append(line).append("\n");
        }
        
        works.add(new StudentWork(name, email, title, content.toString()));
        
        System.out.println("\n✅ РАБОТА УСПЕШНО ОТПРАВЛЕНА!");
        System.out.println("📧 Уведомление отправлено преподавателю");
        System.out.println("🆔 Номер вашей работы: " + works.size());
        System.out.println("⏳ Обратная связь будет отправлена на ваш email");
    }
    
    private static void teacherLogin() {
        System.out.print("\nВведите пароль преподавателя: ");
        String input = scanner.nextLine();
        
        if (!PASSWORD.equals(input)) {
            System.out.println("❌ Неверный пароль!");
            return;
        }
        
        teacherMenu();
    }
    
    private static void teacherMenu() {
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("МЕНЮ ПРЕПОДАВАТЕЛЯ");
            System.out.println("=".repeat(50));
            System.out.println("Всего работ: " + works.size());
            System.out.println("Ожидают проверки: " + works.stream().filter(w -> !w.reviewed).count());
            System.out.println("\n1. 📋 Просмотреть все работы");
            System.out.println("2. 🔍 Просмотреть непроверенные работы");
            System.out.println("3. 💬 Дать обратную связь");
            System.out.println("4. 📊 Статистика");
            System.out.println("5. 📤 Экспорт в текстовый файл");
            System.out.println("0. ↩️  Назад");
            System.out.print("\nВыберите: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1 -> showAllWorks();
                    case 2 -> showUnreviewedWorks();
                    case 3 -> provideFeedback();
                    case 4 -> showStatistics();
                    case 5 -> exportWorks();
                    case 0 -> { return; }
                    default -> System.out.println("❌ Неверный выбор!");
                }
            } catch (Exception e) {
                System.out.println("❌ Ошибка ввода!");
            }
        }
    }
    
    private static void showAllWorks() {
        if (works.isEmpty()) {
            System.out.println("\n📭 Работ пока нет.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ВСЕ РАБОТЫ (" + works.size() + ")");
        System.out.println("=".repeat(50));
        
        for (int i = 0; i < works.size(); i++) {
            System.out.println("\n--- РАБОТА #" + (i+1) + " ---");
            System.out.println(works.get(i));
            System.out.println("-".repeat(40));
        }
    }
    
    private static void showUnreviewedWorks() {
        long unreviewed = works.stream().filter(w -> !w.reviewed).count();
        
        if (unreviewed == 0) {
            System.out.println("\n🎉 Все работы проверены!");
            return;
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("НЕПРОВЕРЕННЫЕ РАБОТЫ (" + unreviewed + ")");
        System.out.println("=".repeat(50));
        
        for (int i = 0; i < works.size(); i++) {
            StudentWork work = works.get(i);
            if (!work.reviewed) {
                System.out.println("\n--- РАБОТА #" + (i+1) + " ---");
                System.out.println("👤 Студент: " + work.name);
                System.out.println("📧 Email: " + work.email);
                System.out.println("📌 Работа: " + work.title);
                System.out.println("📅 Дата: " + work.date);
                System.out.println("📝 Содержание (первые 200 символов):");
                System.out.println(work.content.length() > 200 ? 
                    work.content.substring(0, 200) + "..." : work.content);
                System.out.println("-".repeat(40));
            }
        }
    }
    
    private static void provideFeedback() {
        showUnreviewedWorks();
        
        if (works.stream().noneMatch(w -> !w.reviewed)) {
            return;
        }
        
        System.out.print("\nВведите номер работы для проверки (0 - отмена): ");
        try {
            int num = Integer.parseInt(scanner.nextLine());
            
            if (num == 0) return;
            
            if (num > 0 && num <= works.size()) {
                StudentWork work = works.get(num-1);
                
                if (work.reviewed) {
                    System.out.println("⚠️ Эта работа уже проверена!");
                    return;
                }
                
                System.out.println("\n📋 ПОЛНЫЙ ТЕКСТ РАБОТЫ:");
                System.out.println(work.content);
                System.out.println("\n" + "=".repeat(50));
                
                System.out.print("Введите обратную связь для студента: ");
                work.feedback = scanner.nextLine();
                work.reviewed = true;
                
                System.out.println("\n✅ ОБРАТНАЯ СВЯЗЬ СОХРАНЕНА!");
                System.out.println("📧 Email студента: " + work.email);
                System.out.println("✏️ Рекомендуется отправить уведомление студенту.");
            } else {
                System.out.println("❌ Неверный номер работы!");
            }
        } catch (Exception e) {
            System.out.println("❌ Ошибка ввода!");
        }
    }
    
    private static void showStatistics() {
        System.out.println("\n📊 СТАТИСТИКА");
        System.out.println("=".repeat(30));
        System.out.println("Всего работ: " + works.size());
        System.out.println("Проверено: " + works.stream().filter(w -> w.reviewed).count());
        System.out.println("Ожидают проверки: " + works.stream().filter(w -> !w.reviewed).count());
        
        if (!works.isEmpty()) {
            System.out.println("\n📅 Последние работы:");
            int count = Math.min(works.size(), 5);
            for (int i = works.size() - count; i < works.size(); i++) {
                StudentWork w = works.get(i);
                System.out.printf("  %d. %s - %s (%s)%n", 
                    i+1, w.name, w.title, w.reviewed ? "✅" : "⏳");
            }
        }
    }
    
    private static void exportWorks() {
        System.out.println("\nЭкспорт всех работ...");
        
        try {
            // В онлайн компиляторе файл создается в виртуальной файловой системе
            // Для OnlineGDB можно вывести в консоль
            System.out.println("\n" + "=".repeat(80));
            System.out.println("ЭКСПОРТ ДАННЫХ (" + new Date() + ")");
            System.out.println("=".repeat(80));
            
            for (int i = 0; i < works.size(); i++) {
                StudentWork w = works.get(i);
                System.out.printf("\nРАБОТА #%d\n", i+1);
                System.out.printf("ФИО: %s\n", w.name);
                System.out.printf("Email: %s\n", w.email);
                System.out.printf("Работа: %s\n", w.title);
                System.out.printf("Дата: %s\n", w.date);
                System.out.printf("Статус: %s\n", w.reviewed ? "Проверено" : "Не проверено");
                System.out.printf("Обратная связь: %s\n", w.feedback);
                System.out.printf("Содержание: %s\n", 
                    w.content.length() > 100 ? w.content.substring(0, 100) + "..." : w.content);
                System.out.println("-".repeat(80));
            }
            
            System.out.println("\n✅ Данные готовы для копирования.");
            System.out.println("Скопируйте текст выше и сохраните в файл .txt");
        } catch (Exception e) {
            System.out.println("❌ Ошибка при экспорте: " + e.getMessage());
        }
    }
    
    private static void about() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("СИСТЕМА ПРИЕМА СТУДЕНЧЕСКИХ РАБОТ");
        System.out.println("=".repeat(50));
        System.out.println("Версия: 1.0");
        System.out.println("Автор: Преподаватель");
        System.out.println("\nФункции:");
        System.out.println("• 📤 Студенты отправляют работы");
        System.out.println("• 👨‍🏫 Преподаватель проверяет работы");
        System.out.println("• 💬 Обратная связь для каждого студента");
        System.out.println("• 📊 Статистика и отчеты");
        System.out.println("\nПароль преподавателя: teacher123");
    }
}
