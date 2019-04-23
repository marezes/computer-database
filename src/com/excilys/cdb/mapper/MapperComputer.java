package com.excilys.cdb.mapper;

import java.sql.Timestamp;

import com.excilys.cdb.dto.DTOComputer;
import com.excilys.cdb.model.ModelComputer;

public class MapperComputer {
    public MapperComputer() {
    }

    public DTOComputer modelToDTO(ModelComputer modelComputer) {
        return (new DTOComputer(String.valueOf(modelComputer.getId()),
                modelComputer.getName(), modelComputer.getDi().toString(),
                modelComputer.getDd().toString(), modelComputer.getManufacturer()));
    }

    public ModelComputer dtoToModel(DTOComputer dtoComputer) {
        int id = -1;
        String name;
        Timestamp di = null;
        Timestamp dd = null;
        String company;

        try {
            id = Integer.parseInt(dtoComputer.getId());
        } catch (NumberFormatException nfe) {
            System.err.println("Pas un integer pour id");
        }

        name = dtoComputer.getName();

        try {
            di = Timestamp.valueOf(dtoComputer.getDi());
        } catch (IllegalArgumentException iae) {
            System.err.println("Pas de Timestamp di");
        }

        try {
            Timestamp.valueOf(dtoComputer.getDd());
        } catch (IllegalArgumentException iae) {
            System.err.println("Pas de Timestamp dd");
        }

        company = dtoComputer.getManufacturer();

        return (new ModelComputer(id, name, di, dd, company));
    }
}
