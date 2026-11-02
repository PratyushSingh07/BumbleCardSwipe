@file:OptIn(InternalResourceApi::class)

package com.github.cardswipe.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceContentHash
import org.jetbrains.compose.resources.ResourceItem

private const val MD: String = "composeResources/com.github.cardswipe.resources/"

@delegate:ResourceContentHash(-317_912_413)
internal val Res.font.poppins_bold: FontResource by lazy {
      FontResource("font:poppins_bold", setOf(
        ResourceItem(setOf(), "${MD}font/poppins_bold.ttf", -1, -1),
      ))
    }

@delegate:ResourceContentHash(992_584_762)
internal val Res.font.poppins_regular: FontResource by lazy {
      FontResource("font:poppins_regular", setOf(
        ResourceItem(setOf(), "${MD}font/poppins_regular.ttf", -1, -1),
      ))
    }

@delegate:ResourceContentHash(429_221_087)
internal val Res.font.poppins_semi_bold: FontResource by lazy {
      FontResource("font:poppins_semi_bold", setOf(
        ResourceItem(setOf(), "${MD}font/poppins_semi_bold.ttf", -1, -1),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainFont0Resources(map: MutableMap<String, FontResource>) {
  map.put("poppins_bold", Res.font.poppins_bold)
  map.put("poppins_regular", Res.font.poppins_regular)
  map.put("poppins_semi_bold", Res.font.poppins_semi_bold)
}
