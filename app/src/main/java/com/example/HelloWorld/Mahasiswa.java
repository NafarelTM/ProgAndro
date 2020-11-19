package com.example.HelloWorld;

public class Mahasiswa {
    private String nim;
    private String nama;
    private String phoneNo;

    public Mahasiswa(String nim, String nama, String phoneNo) {
        this.nim = nim;
        this.nama = nama;
        this.phoneNo = phoneNo;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhone(String phone) {
        this.phoneNo = phone;
    }

}
