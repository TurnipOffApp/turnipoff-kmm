//
//  PersonViewModel.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 28/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Combine
import shared

final class PersonViewModel: ObservableObject {

    private var subscriptions = Set<AnyCancellable>()
    private let id: Int64
    @Published private(set) var person: Person?
    @Published private(set) var credits: MovieCredits?

    init(id: Int64) {
        self.id = id
    }
}

extension PersonViewModel {
    
    @MainActor
    func refreshPerson() async {
        do {
            let result: Person = try await Service.shared.client.getPerson(personId: id)
        
            self.person = result
        } catch {
            self.person = nil
        }
    }

    @MainActor
    func refreshCredits() async {
        do {
            let result: MovieCredits = try await Service.shared.client.getPersonCredits(personId: id)
        
            self.credits = result
        } catch {
            self.credits = nil
        }
    }
}

private extension PersonViewModel {

    func refreshCredits() {
        /*TMDBClient
            .shared
            .credits(forPeople: id)
            .receive(on: RunLoop.main)
            .sink { _ in } receiveValue: { credits in
                self.credits = credits
            }
            .store(in: &subscriptions)*/
    }

}
