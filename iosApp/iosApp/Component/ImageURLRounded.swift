//
//  ImageURLRounded.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 22/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ImageURLRounded: View {

    let url: URL?
    let contentMode: ContentMode

    init(url: URL?, contentMode: ContentMode = .fill) {
        self.url = url
        self.contentMode = contentMode
    }

    var body: some View {
        ImageURL(url: url, contentMode: contentMode)
            .cornerRadius(10)
            .shadow(radius: 5)
    }

}
