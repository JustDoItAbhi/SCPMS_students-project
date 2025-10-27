import { useAuth } from "./UseAuth";
import { Navigate } from "react-router-dom";

const ProtectedRoute = (props) => {
    const { user, loading } = useAuth();

    console.log("ProtectedRoute - requiredRole:", props.requiredRole);
    console.log("ProtectedRoute - user:", user);
    console.log("ProtectedRoute - loading:", loading);
    
    const userRole = user?.roles?.[0];
    console.log("User role:", userRole);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!user) {
        console.log("No user in auth context, redirecting to login");
        return <Navigate to="/login" replace />;
    }

    // // Check if specific role is required
    if (props.requiredRole && userRole !== props.requiredRole) {
        console.log(`Role mismatch: User has ${userRole}, required ${props.requiredRole}`);
        return <Navigate to="/unauthorized" replace />;
    }

    return props.children;
};

export default ProtectedRoute;