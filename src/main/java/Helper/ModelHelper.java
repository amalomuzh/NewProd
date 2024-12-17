package Helper;

import model.ToDoModel;
import java.util.Collection;

public class ModelHelper {
    public static <T extends ToDoModel> T findModelById(Collection<T> models, int id) {
        return models.stream()
                .filter(model -> model.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
