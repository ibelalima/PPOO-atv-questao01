/**
 * Watches the search queries
 */
public class Snooper {
    private final WebSearchModel model;

    public Snooper(WebSearchModel model) {
        this.model = model;
    WebSearchModel.QueryFilter friendFilter = new WebSearchModel.QueryFilter() {
            @Override
            public boolean shouldNotify(String query) {
                return query.toLowerCase().contains("friend");
            }
        };

        // Observador para consultas com "friend"
        WebSearchModel.QueryObserver friendObserver = new WebSearchModel.QueryObserver() {
            @Override
            public void onQuery(String query) {
                System.out.println("Oh Yes! " + query);
            }
        };

        // Filtro que aceita consultas com mais de 60 caracteres
        WebSearchModel.QueryFilter longFilter = new WebSearchModel.QueryFilter() {
            @Override
            public boolean shouldNotify(String query) {
                return query.length() > 60;
            }
        };

        // Observador para consultas longas
        WebSearchModel.QueryObserver longObserver = new WebSearchModel.QueryObserver() {
            @Override
            public void onQuery(String query) {
                System.out.println("So long " + query);
            }
        };

        // Registra ambos os observadores com seus filtros (strategies)
        model.addQueryObserver(friendObserver, friendFilter);
        model.addQueryObserver(longObserver, longFilter);
    }
}
