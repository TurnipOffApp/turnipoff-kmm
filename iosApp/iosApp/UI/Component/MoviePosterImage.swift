//
//  ImageURLRounded.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 22/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct MoviePosterImage: View {

    private let url: URL?

    init(url: URL?) {
        self.url = url
    }

    var body: some View {
        ImageURL(
            url: url,
            contentMode: .fit,
            ratio: 2/3
        )
            .cornerRadius(10)
    }

}
