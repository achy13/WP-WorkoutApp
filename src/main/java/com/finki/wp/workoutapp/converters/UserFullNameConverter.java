package com.finki.wp.workoutapp.converters;

import com.finki.wp.workoutapp.model.UserFullName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserFullNameConverter implements AttributeConverter<UserFullName, String> {
    private static final String SEPARATOR = ", ";


    @Override
    public String convertToDatabaseColumn(UserFullName fullname) {
        if (fullname == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (fullname.getSurname() != null && !fullname.getSurname()
                .isEmpty()) {
            sb.append(fullname.getSurname());

        }

        if (fullname.getName() != null
                && !fullname.getName().isEmpty()) {
            sb.append(SEPARATOR);
            sb.append(fullname.getName());
        }

        return sb.toString();
    }

    @Override
    public UserFullName convertToEntityAttribute(String dbUserFullname) {
        if (dbUserFullname == null || dbUserFullname.isEmpty()) {
            return null;
        }

        String[] pieces = dbUserFullname.split(SEPARATOR);

        if (pieces.length == 0) {
            return null;
        }

        UserFullName fullName = new UserFullName();
        String firstPiece = !pieces[0].isEmpty() ? pieces[0] : null;
        if (dbUserFullname.contains(SEPARATOR)) {
            fullName.setSurname(firstPiece);

            if (pieces.length >= 2 && pieces[1] != null
                    && !pieces[1].isEmpty()) {
                fullName.setName(pieces[1]);
            }
        } else {
            fullName.setName(firstPiece);
        }

        return fullName;
    }
}

