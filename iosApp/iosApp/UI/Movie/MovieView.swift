//
//  MovieView.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 27/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct MovieView: View {

    @StateObject var viewModel: MovieViewModel

    var body: some View {
        GeometryReader { geometry in
            ScrollView {
                VStack(alignment: .leading) {
                    if let movie = viewModel.movie {
                        MovieHeaderView(movie: movie).frame(width: geometry.size.width, height: geometry.size.height * 0.3)
                        Divider()
                        MovieDescriptionView(movie: movie)
                        Divider()
                        MovieOverviewView(movie: movie)
                    }
                    if let credits = viewModel.credits {
                        Divider()
                        CreditsView(credits: credits)
                            .frame(height: geometry.size.height * 0.75)
                    }
                }
                .frame(width: geometry.size.width)
            }
            .navigationTitle("Movie details")
            .onAppear {
                Task {
                    await viewModel.refreshMovie()
                    await viewModel.refreshCredits()
                }
            }
        }
    }
}

