package io.tguduru.spring.jdbc.entity;

/**
 * @author Guduru, Thirupathi Reddy
 *         created 10/7/18
 */
public class Region {
    private final long id;
    private final String name;

    public Region(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Region{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
