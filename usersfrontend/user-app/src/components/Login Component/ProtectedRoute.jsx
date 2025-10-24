import { useAuth } from "./UseAuth";

 const ProtectedRoute = ({ children }) => {
    const { user, loading } = useAuth();

    console.log("ProtectedRoute - user:", user);
    console.log("ProtectedRoute - loading:", loading);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!user) {
        console.log("No user, redirecting to login");
        // return <Navigate to="/login" replace />;
    }

    console.log("User authenticated, rendering children");
    return children;
};
export default ProtectedRoute;