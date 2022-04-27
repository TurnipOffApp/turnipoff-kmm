//
//  MovieOverviewView.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 27/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MovieOverviewView: View {

    // MARK: Properties

    let movie: Movie

    // MARK: View

    var body: some View {
        Group {
            if movie.overview.isEmpty {
                Text("No overview.")
            } else {
                Text(movie.overview)
            }
        }
            .padding()
    }

}

