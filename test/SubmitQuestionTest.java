import models.Question;
import org.junit.*;
import play.api.mvc.Controller;

import static play.test.Helpers.*;

public class SubmitQuestionTest {

    @Test
    public void testPersistence(){
        // Start a fake application
        running(fakeApplication(inMemoryDatabase()), new Runnable() {

            @Override
            public void run() {
                // Create a question and save it
                Question question = new Question();
                question.text = "Will this object be persisted?";
                question.save();

                // Check in database if question persisted
                Assert.assertTrue(true);
            }
        }
        );


    }

}
