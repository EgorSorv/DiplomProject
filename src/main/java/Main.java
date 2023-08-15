public class Main {
  public static void main(String[] args) {
        JFrame window = new JFrame(); // создание окна
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // закрытие окна по нажатию на кнопку "X"
        window.setResizable(false); // фиксирование размера окна
        window.setTitle("Game"); // название окна

        window.setLocationRelativeTo(null); // отображение окна по центру
        window.setVisible(true); // установка отображения окна
    }
}
