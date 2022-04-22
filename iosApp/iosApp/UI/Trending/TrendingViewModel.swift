//
//  TrendingViewModel.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 22/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Combine
import shared

final class TrendingViewModel: ObservableObject {

    @Published private(set) var movies: [MovieSearchResult]

    init() {
        movies = .init()
    }

    @MainActor
    func getTrendingList() async {
        do {
            let result: TheMovieDBResponse<MovieSearchResult> = try await Service.shared.client.trending(mediaType: .movie, timeWindow: .week)
        
            movies = result.results as? [MovieSearchResult] ?? []
        } catch {
            movies = []
        }
    }
}
