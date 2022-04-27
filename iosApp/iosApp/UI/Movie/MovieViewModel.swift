//
//  MovieViewModel.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 27/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Combine
import shared

final class MovieViewModel: ObservableObject {

    private var subscriptions = Set<AnyCancellable>()
    private let id: Int64
    @Published private(set) var movie: Movie?
    @Published private(set) var credits: MovieCredits?

    init(id: Int64) {
        self.id = id
    }
}

extension MovieViewModel {
    
    @MainActor
    func refreshMovie() async {
        do {
            let result: Movie = try await Service.shared.client.getMovie(movieId: id)
        
            self.movie = result
        } catch {
            self.movie = nil
        }
    }

    @MainActor
    func refreshCredits() async {
        do {
            let result: MovieCredits = try await Service.shared.client.getMovieCredits(movieId: id)
        
            self.credits = result
        } catch {
            self.credits = nil
        }
    }
}
