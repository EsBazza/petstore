-- Flyway Migration: V2__seed_data.sql
-- Initial seed data for Petstore application

INSERT INTO pet (name, description, price, image_url, created_at, updated_at) VALUES
('Buddy', 'A friendly golden retriever who loves to play fetch and is great with kids. Very energetic and loyal companion.', 450.00, 'https://images.unsplash.com/photo-1552053831-71594a27632d?auto=format&fit=crop&q=80&w=800', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Mittens', 'An adorable calico cat with a calm temperament. Loves to curl up on laps and enjoys a quiet environment.', 150.00, 'https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&q=80&w=800', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Charlie', 'A curious beagle with a powerful nose. Always looking for an adventure and loves meeting new people.', 380.00, 'https://images.unsplash.com/photo-1537151608828-ea2b11777ee8?auto=format&fit=crop&q=80&w=800', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Goldie', 'A vibrant goldfish that will brighten up any room. Easy to care for and perfect for first-time pet owners.', 15.00, 'https://images.unsplash.com/photo-1522069169874-c58ec4b76be5?auto=format&fit=crop&q=80&w=800', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Rex', 'A majestic German Shepherd with excellent training. Highly intelligent and protective, ideal for a family home.', 600.00, 'https://images.unsplash.com/photo-1589941013453-ec89f33b5e95?auto=format&fit=crop&q=80&w=800', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Snowball', 'A fluffy white rabbit who is very gentle and enjoys eating carrots. Loves to hop around in a safe garden.', 45.00, 'https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&q=80&w=800', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Coco', 'A playful chocolate labradoodle that is hypoallergenic and very affectionate. Great for families with allergies.', 550.00, 'https://images.unsplash.com/photo-1591768793355-74d7ca701cd4?auto=format&fit=crop&q=80&w=800', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Oliver', 'A sleek black cat who is quite independent but loves occasional head scratches. Very clean and well-behaved.', 120.00, 'https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&q=80&w=800', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
