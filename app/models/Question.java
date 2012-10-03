package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Question extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String text;

    @Constraints.Required
    public String answer;

    public boolean isAnswered = false;

    public static Finder<Long, Question> find =
            new Finder<Long, Question>(Long.class, Question.class);


}
