package org.bialydunajec.gallery.presentation.rest.v1

import org.bialydunajec.gallery.application.CampGalleryProvider
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/camp-gallery")
internal class CampGalleryController(private val campGalleryProvider: CampGalleryProvider) {

    @GetMapping("/{campEditionId}")
    fun getAlbumListByCampEdition(@PathVariable campEditionId: String)
            = campGalleryProvider.getAlbumListByCampEdition(campEditionId)

    @GetMapping("/album/{albumId}")
    fun getFirstPagePhotosInAlbum(@PathVariable albumId: String)
            = campGalleryProvider.getFirstPagePhotosInAlbum(albumId)

    @GetMapping("/album/{albumId}/remaining")
    fun getRemainingPhotosInAlbum(@PathVariable albumId: String)
            = campGalleryProvider.getRemainingPhotosInAlbum(albumId)
}
