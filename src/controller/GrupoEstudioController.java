package controller;

import model.Estudiante;
import model.GrupoEstudio;

import java.util.*;

public class GrupoEstudioController {
    private List<GrupoEstudio> grupos;

    public GrupoEstudioController() {
        this.grupos = new LinkedList<>();
    }

    public void generarGruposPorIntereses(List<Estudiante> estudiantes) {
        Map<String, GrupoEstudio> gruposPorTema = new HashMap<>();

        for (Estudiante e : estudiantes) {
            for (String interes : e.getIntereses()) {
                gruposPorTema.putIfAbsent(interes.trim().toLowerCase(), new GrupoEstudio(interes.trim()));
                GrupoEstudio grupo = gruposPorTema.get(interes.trim().toLowerCase());

                if (!grupo.getParticipantes().contains(e)) {
                    grupo.agregarParticipante(e);
                    e.getGrupos().add(grupo);
                }
            }
        }

        grupos = new ArrayList<>(gruposPorTema.values());
    }

    public List<GrupoEstudio> getGrupos() {
        return grupos;
    }

    public List<GrupoEstudio> obtenerGruposDe(Estudiante e) {
        return e.getGrupos();
    }
}
