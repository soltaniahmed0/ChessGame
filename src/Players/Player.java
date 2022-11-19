package Players;

public class Player {

    private String name;
    private int score = 0;
    private int rank = 0;
    private String color;
    public int kx, ky;

    public Player() {
    }

    public Player(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRank() {
        return rank;
    }

    public String getColor() {
        return color;
    }

    public int getKx() {
        return kx;
    }

    public int getKy() {
        return ky;
    }

    public void setKx(int kx) {
        this.kx = kx;
    }

    public void setKy(int ky) {
        this.ky = ky;
    }

}
