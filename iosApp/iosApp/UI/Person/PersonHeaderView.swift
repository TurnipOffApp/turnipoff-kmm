//
//  PersonHeaderView.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 28/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct PersonHeaderView: View {

    let person: Person

    var body: some View {
        HStack() {
            Spacer()
            MoviePosterImage(
                url: URL(string: PictureSizes.Poster.w185.buildURL(path: person.profilePath ?? ""))
            )
            Spacer()
        }
        .padding()
    }

}
