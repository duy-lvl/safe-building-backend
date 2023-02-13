//package com.safepass.safebuilding.common.firebase.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class ImageController {
//    @Autowired
//    IImageService imageService;
//
//
//    @PostMapping
//    public ResponseEntity create(@RequestParam(name = "file") MultipartFile[] files) {
//
//        for (MultipartFile file : files) {
//
//            try {
//
//                String fileName = imageService.save(file);
//
//                String imageUrl = imageService.getImageUrl(fileName);
//
//                // do whatever you want with that
//
//            } catch (Exception e) {
//                //  throw internal error;
//            }
//        }
//
//        return ResponseEntity.ok().build();
//    }
//
//}
