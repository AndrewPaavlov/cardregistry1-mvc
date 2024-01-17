package com.andreypaavlov.cardregistry.entities;

public enum BloodType {
    U_U("Не установлена"),
    O_P("O(I) Rh+"),
    O_N("O(I) Rh−"),
    A_P("A(II) Rh+"),
    A_N("A(II) Rh-"),
    B_P("B (III) Rh+"),
    B_N("B (III) Rh-"),
    AB_P("AB (IV) Rh+"),
    AB_N("AB (IV) Rh-");

    private  String value;

    public String getValue() {
        return value;
    }

    BloodType(String value) {
        this.value = value;
    }
    public static BloodType findByAnnotation(String annotation) {
        for (BloodType b : BloodType.values()) {
            if (b.getValue().equals(annotation)) {
                return b;
            }
        }
        return null; // или выбросить исключение, если требуется
    }
}
