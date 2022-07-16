package com.apps.albayt.model;

import java.io.Serializable;

public class ContractorDataModel extends StatusResponse implements Serializable {
    private ContractorModel data;

    public ContractorModel getData() {
        return data;
    }
}
