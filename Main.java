import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

public class Main {
    // Напишите приложение, которое будет запрашивать у пользователя следующие
    // данные в произвольном порядке, разделенные пробелом:
    // Фамилия Имя Отчество датарождения номертелефона пол
    // Форматы данных:
    // фамилия, имя, отчество - строки
    // датарождения - строка формата dd.mm.yyyy
    // номертелефона - целое беззнаковое число без форматирования
    // пол - символ латиницей f или m.
    // Приложение должно проверить введенные данные по количеству.
    // Если количество не совпадает с требуемым, вернуть код ошибки,
    // обработать его и показать пользователю
    // сообщение, что он ввел меньше и больше данных, чем требуется.
    // Приложение должно попытаться распарсить полученные значения
    // и выделить из них требуемые параметры.
    // Если форматы данных не совпадают, нужно бросить исключение, соответствующее
    // типу проблемы. Можно использовать встроенные типы java и создать свои.
    // Исключение должно быть корректно обработано, пользователю выведено сообщение
    // с информацией, что именно неверно.
    // Если всё введено и обработано верно, должен создаться файл с названием,
    // равным фамилии, в него в одну строку должны записаться полученные данные,
    // вида
    // <Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
    // Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
    // Не забудьте закрыть соединение с файлом.
    // При возникновении
    // проблемы с чтением-записью в файл,
    // исключение должно
    // быть корректно обработано,
    // пользователь должен
    // увидеть стектрейс
    // ошибки.
    public static void main(String[] args) throws ParseException, FileSystemException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "Введите фамилию, имя, отчество, дату рождения (в формате dd.mm.yyyy), номер телефона (число без разделителей) и пол(символ латиницей f или m), разделенные пробелом");
        String input = scanner.nextLine();

        String[] array = input.split(" ");
        if (array.length != 6) {
            throw new IllegalArgumentException("Колличество данных неверное");
        }
        String lastname = array[0];
        String firstName = array[1];
        String middleName = array[2];

        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        Date birthdate;
        try {
            birthdate = format.parse(array[3]);
        } catch (ParseException e) {
            throw new ParseException("Неверный формат даты рождения", e.getErrorOffset());
        }

        int phoneNumber;
        try {
            phoneNumber = Integer.parseInt(array[4]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Неверный формат телефона");
        }

        String gender = array[5];
        if (!gender.toLowerCase().equals("m") && !gender.toLowerCase().equals("f")) {
            throw new RuntimeErrorException(null, "Неверно введен пол");
        }

        String fileName = "files" + lastname.toLowerCase() + ".txt";
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            if (file.length() > 0) {
                fileWriter.write('\n');
            }
            fileWriter.write(String.format("%s %s %s %s %s %s", lastname, firstName, middleName,
                    birthdate, phoneNumber, gender));
        } catch (IOException e) {
            throw new FileSystemException("Возникла ошибка при работе с файлом");
        }
    }
}
