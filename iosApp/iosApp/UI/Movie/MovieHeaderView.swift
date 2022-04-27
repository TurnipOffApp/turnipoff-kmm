//
//  MovieHeaderView.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 27/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MovieHeaderView: View {

    let movie: Movie

    var body: some View {
        HStack() {
            Spacer()
            MoviePosterImage(
                url: URL(string: PictureSizes.Poster.w500.buildURL(path: movie.posterPath ?? ""))
            )
            Spacer()
        }
        .padding()
    }

}
