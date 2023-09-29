import controller.OrderController;
import model.Data;
import view.Tampilan;

public class Main {
    public static void main(String[] args) {
        try {
            Data data = new Data();
            Tampilan tampilan = new Tampilan();
            OrderController orderController = new OrderController(data, tampilan);

            orderController.jalankanAplikasi();
        } catch (Exception e) {
            System.err.println("Terjadi kesalahan: " + e.getMessage());
        }
    }
}
