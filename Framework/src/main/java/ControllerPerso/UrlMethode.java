package ControllerPerso;

public class UrlMethode {

    private final String url;
    private final String httpMethod; // "GET" ou "POST"

    public UrlMethode(String url, String httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod.toUpperCase();
    }

    // Obligatoire : permet à la Map de comparer deux clés
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlMethode)) return false;
        UrlMethode other = (UrlMethode) o;
        return this.url.equals(other.url) && this.httpMethod.equals(other.httpMethod);
    }

    // Obligatoire avec equals : pour que HashMap fonctionne correctement
    @Override
    public int hashCode() {
        return (url + ":" + httpMethod).hashCode();
    }

    public String getUrl()        { return url; }
    public String getHttpMethod() { return httpMethod; }
}