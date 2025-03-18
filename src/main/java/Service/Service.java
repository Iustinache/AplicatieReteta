package Service;

import Domain.Reteta;
import Repository.IRepository;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.*;
import java.util.stream.Collectors;

public class Service {
    protected IRepository<Reteta> repo;

    public Service(IRepository<Reteta> repo1) {
        repo = repo1;
    }

    // nu se vor folosi update si remove
    public List<Reteta> getReteteDisponibile() {
        List<Reteta> piese = null;
        try {
            piese = repo.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        piese.sort(Comparator.comparing(Reteta::getNrIngrediente).thenComparing(Reteta::getNume));
        return piese;
    }


    public List<Reteta> filtreazaRetete(String textCautare) {
        if (textCautare == null || textCautare.trim().isEmpty()) {
            return getReteteDisponibile(); // Returnează toate retetele dacă nu există text de căutare
        }

        String textCautareLower = textCautare.toLowerCase();

        return getReteteDisponibile().stream()
                .filter(r ->
                        r.getNume().toLowerCase().contains(textCautareLower) ||
                                r.getIngrediente().toLowerCase().contains(textCautareLower)
                )
                .collect(Collectors.toList());
    }

    public List<Reteta> reseteazaFiltrarea() {
        return getReteteDisponibile();
    }


    public void addReteta(String nume, int minute, String ingrediente) throws Exception {
        List<Reteta> retete = repo.findAll();
        for(Reteta r: retete) {
            if(r.getNume().equals(nume) ) {
                throw new IllegalArgumentException("Nu puteti adauga o reteta cu acelasi nume! Si durata de gatire nu poate fi negativa!");
            }
        }

        if (nume == null || nume.isEmpty() ||
                ingrediente == null || ingrediente.isEmpty()) {
            throw new IllegalArgumentException("Toate câmpurile trebuie completate!");
        }

        if(minute < 0 )
            throw new IllegalArgumentException("Durata de gatire trebuie sa fie un numar intreg pozitiv!");


        int id = repo.findAll().stream()
                .mapToInt(Reteta::getId)
                .max()
                .orElse(0) + 1;

        Reteta p = new Reteta(id, nume, minute, ingrediente);

        repo.add(p);
    }


//------CRUD---------------(nu se vor folosi)------------


    public Collection<Reteta> findAllRetete() throws Exception {
        return repo.findAll();
    }




}
