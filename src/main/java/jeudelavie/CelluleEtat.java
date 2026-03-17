package jeudelavie;

/* Tout état doit savoir comment réagir à la vie, la mort, et dire si il est vivant */

public interface CelluleEtat {
    CelluleEtat vit();
    CelluleEtat meurt();
    boolean estVivante();
}