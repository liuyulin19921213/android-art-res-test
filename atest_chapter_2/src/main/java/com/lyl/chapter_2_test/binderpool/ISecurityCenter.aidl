package com.lyl.chapter_2_test.binderpool;


interface ISecurityCenter {

    String encrypt(String content);
    String decrypt(String password);
}