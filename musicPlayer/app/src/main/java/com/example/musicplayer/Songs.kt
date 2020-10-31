package com.example.musicplayer

class Songs {
    private var imagem : String
    private var nome : String

    constructor(imagem: String, nome: String){
        this.imagem = imagem
        this.nome = nome
    }

    fun getImage(): String{
        return this.imagem
    }

    fun getNome(): String{
        return this.nome
    }
}