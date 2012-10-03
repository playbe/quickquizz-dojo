package controllers;

import models.Question;
import org.apache.commons.lang3.StringUtils;
import play.*;
import play.data.Form;
import play.mvc.*;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import views.html.*;

import java.util.List;

public class Application extends Controller {

  public static Form<Question> f =form(Question.class);

  public static Result question() {
    return ok(question.render(f));
  }

  public static Result submitQuestion() {
      Form<Question> formQuestion = f.bindFromRequest();
      if(formQuestion.hasErrors()) {
          return badRequest(question.render(formQuestion));
      }
      Question question = formQuestion.get();
      question.save();
      try {
          TwitterFactory
                  .getSingleton()
                  .updateStatus(String.format("%s [#qq%d]", question.text, question.id));
      } catch (TwitterException e) {
          return internalServerError(e.getMessage());
      }
      return redirect(routes.Application.list());
  }

    public static Result list() {
        List<Question> all = Question.find.all();
        return ok(list.render(all));
    }
}