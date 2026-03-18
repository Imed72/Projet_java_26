-- Table des techniciens
CREATE TABLE techniciens (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    qualification VARCHAR(100),
    disponible BOOLEAN DEFAULT TRUE
);

-- Table des bâtiments
CREATE TABLE batiments (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    localisation VARCHAR(200)
);

-- Table des interventions (AVEC LES NOMS DE COLONNES CORRECTS)
CREATE TABLE interventions (
    id SERIAL PRIMARY KEY,
    description TEXT,
    date_intervention DATE NOT NULL,      -- Ton Java cherche "date_intervention"
    type_intervention VARCHAR(50) NOT NULL, -- Ton Java cherche "type_intervention"
    statut VARCHAR(50) NOT NULL DEFAULT 'Planifiée',
    id_technicien INTEGER REFERENCES techniciens(id) ON DELETE SET NULL,
    id_batiment INTEGER REFERENCES batiments(id) ON DELETE CASCADE
);

-- Index
CREATE INDEX idx_inter_tech ON interventions(id_technicien);
CREATE INDEX idx_inter_bat ON interventions(id_batiment);

-- --- DONNÉES D'EXEMPLE ---

INSERT INTO techniciens (nom, qualification, disponible) VALUES
    ('Jean Dupont', 'Électricien', true),
    ('Marie Martin', 'Plombier', true),
    ('Pierre Durand', 'Chauffagiste', true),
    ('Alice Lefebvre', 'Climatisation', true);

INSERT INTO batiments (nom, localisation) VALUES
    ('Siège Social', '123 Rue de l''Église, Paris'),
    ('Entrepôt Nord', '14 Avenue des Champs quantiques, Lyon'),
    ('Usine Est', '76 Boulevard De Gaulle, Marseille');

INSERT INTO interventions (date_intervention, type_intervention, statut, id_technicien, id_batiment, description) VALUES
    ('2026-03-10', 'Réparation', 'Terminée', 1, 1, 'Réparation du tableau électrique'),
    ('2026-03-12', 'Maintenance', 'En cours', 2, 2, 'Débouchage des canalisations'),
    ('2026-03-15', 'Installation', 'Planifiée', 3, 3, 'Installation d''une chaudière');