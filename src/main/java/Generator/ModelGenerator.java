package Generator;

import model.ToDoModel;
import net.datafaker.Faker;

public class ModelGenerator {
    public static Faker faker = new Faker();

    public static ToDoModel createToDoModel(){
        return ToDoModel.builder()
                .id(faker.random().nextInt(0, Integer.MAX_VALUE))
                .text(faker.word().noun())
                .completed(faker.random().nextBoolean()).build();
    }
}
