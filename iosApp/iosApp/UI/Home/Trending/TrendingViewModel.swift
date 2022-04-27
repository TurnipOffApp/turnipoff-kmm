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
            let result: TheMovieDBResponse<MovieSearchResult> = try await Service.shared.client.discover(
                sortby: "vote_average.asc",
                voteCount: 25,
                page: 1,
                genres: nil,
                releaseAfter: nil,
                releaseBefore: nil
            )
            
        
            movies = result.results as? [MovieSearchResult] ?? []
        } catch {
            movies = []
        }
    }
}
