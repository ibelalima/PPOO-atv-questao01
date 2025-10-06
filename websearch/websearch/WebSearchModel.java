import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Perform "web search" (from a  file), notify the interested observers of each query.
 */
public class WebSearchModel {
    private final File sourceFile;

    private static class ObserverEntry{
        QueryObserver observer;
        QueryFilter filter;

        ObserverEntry(QueryObserver observer, QueryFilter filter){
            this.observer = observer;
            this.filter = filter;
        }
    }
    private final List<QueryObserver> observers = new ArrayList<>();

    public interface QueryObserver {
        void onQuery(String query);
    }
    public interface QueryFilter{
        boolean shouldNotify(String query);
    }
    public WebSearchModel(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void addQueryObserver(QueryObserver observer, QueryFilter filter){
        observers.add(new ObserverEntry(observer, filter));
    }

    public void pretendToSearch() {
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
            while ( true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                notifyAllObservers(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addQueryObserver(QueryObserver queryObserver) {
        observers.add(queryObserver);
    }

    private void notifyAllObservers(String line) {
        for (QueryObserver obs : observers) {
            obs.onQuery(line);
        }
    }
}
