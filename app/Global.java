import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.util.Duration;
import models.Question;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: playbe
 * Date: 10/3/12
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Global extends GlobalSettings {
    @Override
    public void onStart(Application application) {
        super.onStart(application);
        ActorRef actorRef = Akka.system().actorOf(new Props(AnswerVal.class));
        Akka.system().scheduler().schedule(
                Duration.create(0, TimeUnit.SECONDS),
                Duration.create(3, TimeUnit.SECONDS),
                actorRef, "tick"
        );
    }

    public static class AnswerVal extends UntypedActor {
        @Override
        public void onReceive(Object o) throws Exception {
            List<Status> statuses = TwitterFactory.getSingleton().getMentions();
            for (Status s : statuses) {
                if (s.getText().contains("#qq")) {
                    String id = s.getText().substring(s.getText().indexOf("#qq") + 3);
                    Long parsedID = Long.valueOf(id);
                    Question q = Question.find.byId(parsedID);
                    if (q != null &&  s.getText().contains(q.answer)) {
                        final String answer;
                        if(!q.isAnswered) {
                            answer = "@" + s.getUser().getScreenName() +
                                    " - You are the winner";
                        } else {
                            answer = "@" + s.getUser().getScreenName() +
                                    " - You were tooooo sloowww";
                        }

                        TwitterFactory
                                .getSingleton()
                                .updateStatus(
                                        new StatusUpdate(answer).
                                                inReplyToStatusId(s.getInReplyToStatusId())

                                );
                        q.isAnswered = true;
                        q.save();
                    }
                }
            }
        }
    }
}
